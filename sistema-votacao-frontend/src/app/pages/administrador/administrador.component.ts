import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface Candidato {
  id: number;
  nome: string;
  partido: string;
  fotoUrl?: string;
}

interface Votacao {
  id: number;
  titulo: string;
  descricao: string;
  candidatos: Candidato[];
}

@Component({
  selector: 'app-administrador',
  standalone: true, // 🔥 Adicionado
  imports: [CommonModule, FormsModule], // 🔥 Importações necessárias
  templateUrl: './administrador.component.html',
  styleUrls: ['./administrador.component.css'] // Opcional, se você tiver o CSS
})
export class AdministradorComponent {
  votacoes: Votacao[] = [];
  proximoIdVotacao = 1;
  proximoIdCandidato = 1;

  // Inputs de nova votação
  novoTitulo: string = '';
  novaDescricao: string = '';

  // Inputs de novo candidato
  nomeCandidato: string = '';
  partidoCandidato: string = '';
  fotoUrlCandidato: string = '';

  votacaoSelecionadaId: number | null = null;

  criarVotacao(): void {
    if (this.novoTitulo.trim() && this.novaDescricao.trim()) {
      this.votacoes.push({
        id: this.proximoIdVotacao++,
        titulo: this.novoTitulo,
        descricao: this.novaDescricao,
        candidatos: []
      });
      this.novoTitulo = '';
      this.novaDescricao = '';
    }
  }

  selecionarVotacao(votacaoId: number): void {
    this.votacaoSelecionadaId = votacaoId;
  }

  adicionarCandidato(): void {
    if (this.votacaoSelecionadaId !== null && this.nomeCandidato.trim() && this.partidoCandidato.trim()) {
      const votacao = this.votacoes.find(v => v.id === this.votacaoSelecionadaId);
      if (votacao) {
        votacao.candidatos.push({
          id: this.proximoIdCandidato++,
          nome: this.nomeCandidato,
          partido: this.partidoCandidato,
          fotoUrl: this.fotoUrlCandidato
        });
        this.nomeCandidato = '';
        this.partidoCandidato = '';
        this.fotoUrlCandidato = '';
      }
    }
  }

  editarCandidato(votacaoId: number, candidatoId: number): void {
    const novoNome = prompt('Novo nome do candidato:');
    const novoPartido = prompt('Novo partido do candidato:');
    const novaFoto = prompt('Nova URL da foto:');
    const votacao = this.votacoes.find(v => v.id === votacaoId);
    const candidato = votacao?.candidatos.find(c => c.id === candidatoId);
    if (candidato && novoNome && novoPartido) {
      candidato.nome = novoNome;
      candidato.partido = novoPartido;
      candidato.fotoUrl = novaFoto || candidato.fotoUrl;
    }
  }

  excluirCandidato(votacaoId: number, candidatoId: number): void {
    const votacao = this.votacoes.find(v => v.id === votacaoId);
    if (votacao) {
      votacao.candidatos = votacao.candidatos.filter(c => c.id !== candidatoId);
    }
  }
}
