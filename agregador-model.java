// 1. Entidades

// Entidade Voto (para armazenamento permanente no PostgreSQL)
@Entity
@Table(name = "votos")
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long candidatoId;
    
    private String usuarioNick;
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    private UUID loteId;
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getCandidatoId() { return candidatoId; }
    public void setCandidatoId(Long candidatoId) { this.candidatoId = candidatoId; }
    
    public String getUsuarioNick() { return usuarioNick; }
    public void setUsuarioNick(String usuarioNick) { this.usuarioNick = usuarioNick; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public UUID getLoteId() { return loteId; }
    public void setLoteId(UUID loteId) { this.loteId = loteId; }
}

// Entidade Lote (para rastreamento dos lotes recebidos)
@Entity
@Table(name = "lotes")
public class Lote {
    @Id
    private UUID id;
    
    private LocalDateTime timestampRecebimento;
    
    private int quantidadeVotos;
    
    private boolean processado;
    
    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public LocalDateTime getTimestampRecebimento() { return timestampRecebimento; }
    public void setTimestampRecebimento(LocalDateTime timestampRecebimento) { this.timestampRecebimento = timestampRecebimento; }
    
    public int getQuantidadeVotos() { return quantidadeVotos; }
    public void setQuantidadeVotos(int quantidadeVotos) { this.quantidadeVotos = quantidadeVotos; }
    
    public boolean isProcessado() { return processado; }
    public void setProcessado(boolean processado) { this.processado = processado; }
}

// Entidade ResultadoAgregado (para armazenar resultados consolidados)
@Entity
@Table(name = "resultados_agregados")
public class ResultadoAgregado {
    @Id
    private Long candidatoId;
    
    private String candidatoNome;
    
    private Long quantidadeVotos;
    
    private LocalDateTime ultimaAtualizacao;
    
    // Getters e Setters
    public Long getCandidatoId() { return candidatoId; }
    public void setCandidatoId(Long candidatoId) { this.candidatoId = candidatoId; }
    
    public String getCandidatoNome() { return candidatoNome; }
    public void setCandidatoNome(String candidatoNome) { this.candidatoNome = candidatoNome; }
    
    public Long getQuantidadeVotos() { return quantidadeVotos; }
    public void setQuantidadeVotos(Long quantidadeVotos) { this.quantidadeVotos = quantidadeVotos; }
    
    public LocalDateTime getUltimaAtualizacao() { return ultimaAtualizacao; }
    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) { this.ultimaAtualizacao = ultimaAtualizacao; }
}

// 2. DTOs (os mesmos usados no coletor)

// DTO para transferência de votos
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
    
    // Getters e Setters
    public List<VotoDTO> getVotos() { return votos; }
    public void setVotos(List<VotoDTO> votos) { this.votos = votos; }
    
    public UUID getLoteId() { return loteId; }
    public void setLoteId(UUID loteId) { this.loteId = loteId; }
    
    public LocalDateTime getTimestampEnvio() { return timestampEnvio; }
    public void setTimestampEnvio(LocalDateTime timestampEnvio) { this.timestampEnvio = timestampEnvio; }
}

// DTO para resultados
public class ResultadoDTO {
    private Long candidatoId;
    private String candidatoNome;
    private Long quantidadeVotos;
    
    // Getters e Setters
    public Long getCandidatoId() { return candidatoId; }
    public void setCandidatoId(Long candidatoId) { this.candidatoId = candidatoId; }
    
    public String getCandidatoNome() { return candidatoNome; }
    public void setCandidatoNome(String candidatoNome) { this.candidatoNome = candidatoNome; }
    
    public Long getQuantidadeVotos() { return quantidadeVotos; }
    public void setQuantidadeVotos(Long quantidadeVotos) { this.quantidadeVotos = quantidadeVotos; }
}

// 3. Repositórios

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    List<Voto> findByLoteId(UUID loteId);
    long countByCandidatoId(Long candidatoId);
}

@Repository
public interface LoteRepository extends JpaRepository<Lote, UUID> {
    List<Lote> findByProcessadoFalse();
}

@Repository
public interface ResultadoAgregadoRepository extends JpaRepository<ResultadoAgregado, Long> {
    List<ResultadoAgregado> findAllByOrderByQuantidadeVotosDesc();
}

// 4. Serviços

@Service
public class VotoService {
    @Autowired
    private VotoRepository votoRepository;
    
    @Autowired
    private LoteRepository loteRepository;
    
    @Autowired
    private ResultadoAgregadoRepository resultadoRepository;
    
    @Transactional
    public void processarVoto(VotoDTO votoDTO) {
        // Criar e salvar o voto
        Voto voto = new Voto();
        voto.setCandidatoId(votoDTO.getCandidatoId());
        voto.setUsuarioNick(votoDTO.getUsuarioNick());
        voto.setTimestamp(votoDTO.getTimestamp());
        votoRepository.save(voto);
        
        // Atualizar o resultado agregado
        atualizarResultadoAgregado(votoDTO.getCandidatoId());
    }
    
    @Transactional
    public void processarLoteVotos(LoteVotosDTO loteDTO) {
        // Verificar se o lote já foi processado
        if (loteRepository.existsById(loteDTO.getLoteId())) {
            return; // Lote já processado, evitar duplicação
        }
        
        // Registrar o lote
        Lote lote = new Lote();
        lote.setId(loteDTO.getLoteId());
        lote.setTimestampRecebimento(LocalDateTime.now());
        lote.setQuantidadeVotos(loteDTO.getVotos().size());
        lote.setProcessado(false);
        loteRepository.save(lote);
        
        // Processar cada voto do lote
        for (VotoDTO votoDTO : loteDTO.getVotos()) {
            Voto voto = new Voto();
            voto.setCandidatoId(votoDTO.getCandidatoId());
            voto.setUsuarioNick(votoDTO.getUsuarioNick());
            voto.setTimestamp(votoDTO.getTimestamp());
            voto.setLoteId(loteDTO.getLoteId());
            votoRepository.save(voto);
        }
        
        // Atualizar os resultados agregados (otimizado para processar em batch)
        Map<Long, Long> votosPorCandidato = loteDTO.getVotos().stream()
                .collect(Collectors.groupingBy(VotoDTO::getCandidatoId, Collectors.counting()));
        
        votosPorCandidato.forEach(this::atualizarResultadoAgregado);
        
        // Marcar lote como processado
        lote.setProcessado(true);
        loteRepository.save(lote);
    }
    
    @Transactional
    public void atualizarResultadoAgregado(Long candidatoId) {
        atualizarResultadoAgregado(candidatoId, 1L);
    }
    
    @Transactional
    public void atualizarResultadoAgregado(Long candidatoId, Long incremento) {
        ResultadoAgregado resultado = resultadoRepository.findById(candidatoId)
                .orElse(new ResultadoAgregado());
        
        if (resultado.getCandidatoId() == null) {
            resultado.setCandidatoId(candidatoId);
            resultado.setQuantidadeVotos(0L);
        }
        
        resultado.setQuantidadeVotos(resultado.getQuantidadeVotos() + incremento);
        resultado.setUltimaAtualizacao(LocalDateTime.now());
        
        resultadoRepository.save(resultado);
    }
    
    public List<ResultadoDTO> obterResultados() {
        List<ResultadoAgregado> resultados = resultadoRepository.findAllByOrderByQuantidadeVotosDesc();
        
        return resultados.stream()
                .map(r -> {
                    ResultadoDTO dto = new ResultadoDTO();
                    dto.setCandidatoId(r.getCandidatoId());
                    dto.setCandidatoNome(r.getCandidatoNome());
                    dto.setQuantidadeVotos(r.getQuantidadeVotos());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

// 5. Receptores RabbitMQ

@Component
public class VotoReceiver {
    @Autowired
    private VotoService votoService;
    
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receberVoto(VotoDTO votoDTO) {
        votoService.processarVoto(votoDTO);
    }
    
    @RabbitListener(queues = "${rabbitmq.queue.lote.name}")
    public void receberLoteVotos(LoteVotosDTO loteDTO) {
        votoService.processarLoteVotos(loteDTO);
    }
}

// 6. API REST para consultar resultados

@RestController
@RequestMapping("/resultados")
public class ResultadoController {
    @Autowired
    private VotoService votoService;
    
    @GetMapping
    public ResponseEntity<List<ResultadoDTO>> obterResultados() {
        List<ResultadoDTO> resultados = votoService.obterResultados();
        return ResponseEntity.ok(resultados);
    }
    
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Object>> obterEstatisticas() {
        Map<String, Object> estatisticas = new HashMap<>();
        
        // Implementar estatísticas adicionais aqui
        
        return ResponseEntity.ok(estatisticas);
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
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}

// 8. Configuração do banco de dados PostgreSQL

@Configuration
public class PostgresConfig {
    // Configurações do PostgreSQL, se necessário
}
