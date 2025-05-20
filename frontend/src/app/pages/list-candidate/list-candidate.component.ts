import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CandidateComponent } from '../../components/candidate/candidate.component';
import { FormsModule } from '@angular/forms';
import { apiBase, apiCore } from '../../service/api';
import SockJS from 'sockjs-client';
import { Client, Message } from '@stomp/stompjs';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { isTokenValid } from '../../utils/auth.utils';

export interface Candidato {
  id: number;
  nome: string;
  foto: string;
  quantidadeVotos: number;
}

export interface CandidatoDTO {
  nome: string;
  foto: string;
}

@Component({
  selector: 'app-list-candidate',
  standalone: true,
  imports: [CommonModule, CandidateComponent, FormsModule],
  templateUrl: './list-candidate.component.html',
  styleUrls: ['./list-candidate.component.css'],
})
export class ListCandidateComponent implements OnInit, OnDestroy {
  candidatos: Candidato[] = [];
  stompClient: Client;

  // Estados da conexão WebSocket
  isConnected = false;
  isConnecting = false;
  error: string | null = null;

  // Modal e formulário
  mostrarModal = false;
  novoCandidato: CandidatoDTO = { nome: '', foto: '' };
  isSubmitting = false;

  constructor(private router: Router) {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://192.168.3.4:9090/ws'),
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });
  }

  ngOnInit(): void {
    this.conectarWebSocket();
    this.carregarCandidatosIniciais();
  }

  ngOnDestroy(): void {
    this.desconectarWebSocket();
  }

  conectarWebSocket(): void {
    if (this.isConnecting || this.isConnected) {
      return;
    }

    this.isConnecting = true;
    this.error = null;

    // Configurar callbacks antes de ativar
    this.stompClient.onConnect = (frame) => {
      console.log('Conectado ao WebSocket:', frame);
      this.isConnected = true;
      this.isConnecting = false;
      this.error = null;

      this.stompClient.subscribe('/topic/candidatos', (message: Message) => {
        try {
          const novoCandidatos = JSON.parse(message.body);
          this.candidatos = Array.isArray(novoCandidatos)
            ? this.ordenarCandidatosPorVotos(novoCandidatos)
            : [];
        } catch (error) {
          this.error = 'Erro ao processar dados recebidos';
        }
      });
    };

    this.stompClient.onStompError = (frame) => {
      this.error = `Erro de conexão WebSocket: ${
        frame.headers['message'] || 'Erro desconhecido'
      }`;
      this.isConnected = false;
      this.isConnecting = false;
    };

    this.stompClient.onWebSocketError = (event) => {
      this.error = 'Erro de conexão WebSocket';
      this.isConnected = false;
      this.isConnecting = false;
    };

    this.stompClient.onDisconnect = () => {
      this.isConnected = false;
      this.isConnecting = false;
    };

    this.stompClient.activate();
  }

  desconectarWebSocket(): void {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.deactivate();
    }
    this.isConnected = false;
    this.isConnecting = false;
  }

  async carregarCandidatosIniciais(): Promise<void> {
    try {
      const response = await apiCore.get('/eleicao-gp2/listarCandidatosDesc');
      this.candidatos = Array.isArray(response.data) ? response.data : [];
    } catch (error) {
      this.candidatos = [];
    }
  }

  reconectar(): void {
    this.desconectarWebSocket();
    setTimeout(() => {
      this.conectarWebSocket();
    }, 1000);
  }

  abrirModal(): void {
    this.mostrarModal = true;
    this.novoCandidato = { nome: '', foto: '' };
    this.error = null;
  }

  fecharModal(): void {
    this.mostrarModal = false;
    this.novoCandidato = { nome: '', foto: '' };
    this.error = null;
    this.isSubmitting = false;
  }

  async adicionarCandidato(): Promise<void> {
    if (this.logout_no_token()) return;

    if (!this.novoCandidato.nome?.trim() || !this.novoCandidato.foto?.trim()) {
      this.error = 'Por favor, preencha todos os campos obrigatórios.';
      return;
    }

    if (this.isSubmitting) return;

    this.isSubmitting = true;
    this.error = null;

    try {
      const token = localStorage.getItem('token');

      const candidatoData = {
        nome: this.novoCandidato.nome.trim(),
        foto: this.novoCandidato.foto.trim(),
      };

      const response = await apiBase.post(
        '/eleicao-gp2/candidato',
        candidatoData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      this.fecharModal();
    } catch (error: any) {
      console.error('Erro ao adicionar candidato:', error);
      this.isSubmitting = false;

      if (error.response?.status === 400) {
        this.error = error.response.data?.message || 'Nome já existe ou dados inválidos.';
      } else if (error.response?.status === 409) {
        this.error = 'Candidato com este nome já existe.';
      } else if (error.response?.status >= 500) {
        this.error = 'Erro interno do servidor. Tente novamente.';
      } else {
        this.error = 'Erro ao adicionar candidato. Verifique sua conexão.';
      }
    }
  }



  votar(candidato_id: Number): void {
    if (this.logout_no_token()) return;

    const token = localStorage.getItem('token');

    apiBase.post(
      `/eleicao-gp2/votar/${candidato_id}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
  }


  logout_no_token(): boolean {
    const token = localStorage.getItem('token');

    if (!token || !isTokenValid(token)) {
      localStorage.removeItem('token');
      this.router.navigate(['/login']);
      alert('Deslogado');
      return true;
    }

    return false;
  }




  isValidImageUrl(url: string): boolean {
    const imagePattern = /\.(jpg|jpeg|png|gif|webp)$/i;
    return (
      imagePattern.test(url) ||
      url.startsWith('data:image/') ||
      url.startsWith('http')
    );
  }

  ordenarCandidatosPorVotos(candidatos: Candidato[]): Candidato[] {
    return candidatos.sort((a, b) => b.quantidadeVotos - a.quantidadeVotos);
  }

  returnNick() {
    const token = localStorage.getItem('token');
    if (!token) return;
    const decoded: any = jwtDecode(token);
    return decoded.nick;
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
    alert('Deslogado');
    return;
  }
}
