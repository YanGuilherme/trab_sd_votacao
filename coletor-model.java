// 1. Entidades

// Entidade Candidato
@Entity
@Table(name = "candidatos")
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String nome;
    
    private Long quantidadeVotos;
    
    private String foto;
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Long getQuantidadeVotos() { return quantidadeVotos; }
    public void setQuantidadeVotos(Long quantidadeVotos) { this.quantidadeVotos = quantidadeVotos; }
    
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}

// Entidade Usuario
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String nick;
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNick() { return nick; }
    public void setNick(String nick) { this.nick = nick; }
}

// Entidade VotoTemporario (para armazenamento local no H2)
@Entity
@Table(name = "votos_temporarios")
public class VotoTemporario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long candidatoId;
    
    private String usuarioNick;
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    private boolean enviado;
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getCandidatoId() { return candidatoId; }
    public void setCandidatoId(Long candidatoId) { this.candidatoId = candidatoId; }
    
    public String getUsuarioNick() { return usuarioNick; }
    public void setUsuarioNick(String usuarioNick) { this.usuarioNick = usuarioNick; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public boolean isEnviado() { return enviado; }
    public void setEnviado(boolean enviado) { this.enviado = enviado; }
}

// 2. DTOs

// DTO para transferência de votos para o agregador
public class VotoDTO {
    private Long candidatoId;
    private String usuarioNick;
    private LocalDateTime timestamp;
    
    // Construtores
    public VotoDTO() {}
    
    public VotoDTO(Long candidatoId, String usuarioNick, LocalDateTime timestamp) {
        this.candidatoId = candidatoId;
        this.usuarioNick = usuarioNick;
        this.timestamp = timestamp;
    }
    
    // Getters e Setters
    public Long getCandidatoId() { return candidatoId; }
    public void setCandidatoId(Long candidatoId) { this.candidatoId = candidatoId; }
    
    public String getUsuarioNick() { return usuarioNick; }
    public void setUsuarioNick(String usuarioNick) { this.usuarioNick = usuarioNick; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

// DTO para lote de votos
public class LoteVotosDTO {
    private List<VotoDTO> votos;
    private UUID loteId;
    private LocalDateTime timestampEnvio;
    
    // Construtores
    public LoteVotosDTO() {
        this.votos = new ArrayList<>();
        this.loteId = UUID.randomUUID();
        this.timestampEnvio = LocalDateTime.now();
    }
    
    // Getters e Setters
    public List<VotoDTO> getVotos() { return votos; }
    public void setVotos(List<VotoDTO> votos) { this.votos = votos; }
    
    public UUID getLoteId() { return loteId; }
    
    public LocalDateTime getTimestampEnvio() { return timestampEnvio; }
    public void setTimestampEnvio(LocalDateTime timestampEnvio) { this.timestampEnvio = timestampEnvio; }
    
    // Métodos utilitários
    public void addVoto(VotoDTO voto) {
        this.votos.add(voto);
    }
    
    public int getTamanho() {
        return this.votos.size();
    }
}

// 3. Repositórios

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    boolean existsByNome(String nome);
    List<Candidato> findAllByOrderByQuantidadeVotosDesc();
}

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByNick(String nick);
    Optional<Usuario> findByNick(String nick);
}

@Repository
public interface VotoTemporarioRepository extends JpaRepository<VotoTemporario, Long> {
    List<VotoTemporario> findByEnviadoFalseOrderByTimestampAsc();
    long countByEnviadoFalse();
}

// 4. Serviços

@Service
public class EleicaoService {
    @Autowired
    private CandidatoRepository candidatoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private VotoTemporarioRepository votoTemporarioRepository;
    
    @Autowired
    private VotoSender votoSender;
    
    public Usuario createUser(UsuarioDTO userDTO) {
        if (usuarioRepository.existsByNick(userDTO.getNick())) {
            throw new RuntimeException("nick ja existe");
        }
        
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNick(userDTO.getNick());
        return usuarioRepository.save(novoUsuario);
    }
    
    public boolean existeUserByNick(String nick) {
        return usuarioRepository.existsByNick(nick);
    }
    
    public List<Usuario> buscarUsers() {
        return usuarioRepository.findAll();
    }
    
    public Candidato createCandidato(CandidatoDTO candidatoDTO) {
        if (candidatoRepository.existsByNome(candidatoDTO.getNome())) {
            throw new RuntimeException("nome ja existe");
        }
        
        Candidato novoCandidato = new Candidato();
        novoCandidato.setNome(candidatoDTO.getNome());
        novoCandidato.setFoto(candidatoDTO.getFoto());
        novoCandidato.setQuantidadeVotos(0L);
        return candidatoRepository.save(novoCandidato);
    }
    
    public List<Candidato> buscarCandidatos() {
        return candidatoRepository.findAll();
    }
    
    public List<Candidato> listarPorQuantidadeVotosDesc() {
        return candidatoRepository.findAllByOrderByQuantidadeVotosDesc();
    }
    
    public List<Candidato> listarCandidatos() {
        return candidatoRepository.findAll();
    }
    
    @Transactional
    public String votar(String usuarioNick, Long candidatoId) {
        // Verificar se o candidato existe
        Candidato candidato = candidatoRepository.findById(candidatoId)
            .orElseThrow(() -> new RuntimeException("Candidato não encontrado"));
        
        // Registrar o voto temporariamente no H2
        VotoTemporario votoTemp = new VotoTemporario();
        votoTemp.setCandidatoId(candidatoId);
        votoTemp.setUsuarioNick(usuarioNick);
        votoTemp.setTimestamp(LocalDateTime.now());
        votoTemp.setEnviado(false);
        votoTemporarioRepository.save(votoTemp);
        
        // Atualizar a contagem local temporária para exibição imediata
        candidato.setQuantidadeVotos(candidato.getQuantidadeVotos() + 1);
        candidatoRepository.save(candidato);
        
        return "Voto computado com sucesso para " + candidato.getNome();
    }
}

// 5. Comunicação RabbitMQ

@Component
public class VotoSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    
    public void enviarVoto(VotoDTO voto) {
        rabbitTemplate.convertAndSend(exchange, routingKey, voto);
    }
    
    public void enviarLoteVotos(LoteVotosDTO lote) {
        rabbitTemplate.convertAndSend(exchange, "votos.lote", lote);
    }
}

// 6. Gerenciamento de lotes (Scheduler)

@Component
public class LoteScheduler {
    @Autowired
    private VotoTemporarioRepository votoTemporarioRepository;
    
    @Autowired
    private VotoSender votoSender;
    
    @Value("${lote.tamanho.maximo}")
    private int tamanhoMaximoLote;
    
    @Value("${lote.tempo.maximo}")
    private int tempoMaximoSegundos;
    
    private LocalDateTime ultimoEnvio = LocalDateTime.now();
    
    @Scheduled(fixedDelayString = "${lote.verificacao.intervalo}")
    @Transactional
    public void enviarLote() {
        // Verificar se existem votos a serem enviados
        long votosAEnviar = votoTemporarioRepository.countByEnviadoFalse();
        
        if (votosAEnviar == 0) {
            return;
        }
        
        // Verificar se atingiu o tamanho máximo ou se excedeu o tempo máximo
        LocalDateTime agora = LocalDateTime.now();
        boolean excedeuTempo = ChronoUnit.SECONDS.between(ultimoEnvio, agora) >= tempoMaximoSegundos;
        boolean atingiuTamanho = votosAEnviar >= tamanhoMaximoLote;
        
        if (excedeuTempo || atingiuTamanho) {
            // Buscar votos não enviados
            List<VotoTemporario> votosTemporarios = votoTemporarioRepository.findByEnviadoFalseOrderByTimestampAsc();
            
            // Criar lote
            LoteVotosDTO lote = new LoteVotosDTO();
            
            // Adicionar votos ao lote
            for (VotoTemporario votoTemp : votosTemporarios) {
                VotoDTO votoDTO = new VotoDTO(
                    votoTemp.getCandidatoId(),
                    votoTemp.getUsuarioNick(),
                    votoTemp.getTimestamp()
                );
                lote.addVoto(votoDTO);
                
                // Marcar como enviado
                votoTemp.setEnviado(true);
            }
            
            // Enviar lote para o agregador
            votoSender.enviarLoteVotos(lote);
            
            // Salvar status dos votos
            votoTemporarioRepository.saveAll(votosTemporarios);
            
            // Atualizar timestamp do último envio
            ultimoEnvio = agora;
        }
    }
}

// 7. Configuração RabbitMQ

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.queue.name}")
    private String queueName;
    
    @Value("${rabbitmq.queue.lote.name}")
    private String queueLoteName;
    
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    
    @Value("${rabbitmq.routing.lote.key}")
    private String routingLoteKey;
    
    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }
    
    @Bean
    public Queue queueLote() {
        return new Queue(queueLoteName);
    }
    
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }
    
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }
    
    @Bean
    public Binding bindingLote() {
        return BindingBuilder
                .bind(queueLote())
                .to(exchange())
                .with(routingLoteKey);
    }
    
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}

// 8. Configuração H2 e agendamento

@Configuration
@EnableScheduling
public class AppConfig {
    @Value("${lote.verificacao.intervalo}")
    private int intervaloVerificacao;
    
    @Value("${lote.tamanho.maximo}")
    private int tamanhoMaximoLote;
    
    @Value("${lote.tempo.maximo}")
    private int tempoMaximoSegundos;
}
