import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CandidateComponent } from '../../components/candidate/candidate.component'; // ajuste o path conforme a sua estrutura
import { FormsModule } from '@angular/forms';
import api from '../../service/api';

@Component({
  selector: 'app-list-candidate',
  standalone: true,
  imports: [CommonModule, CandidateComponent, FormsModule],
  templateUrl: './list-candidate.component.html',
  styleUrls: ['./list-candidate.component.css'],
})
export class ListCandidateComponent implements OnInit {
  candidatos: any[] = [];

  ngOnInit(): void {
    this.buscarCandidatos();
  }

  async buscarCandidatos(): Promise<void> {
    try {
      const response = await api.get('/list');
      this.candidatos = response.data;
    } catch (error) {
      console.error('Erro ao buscar candidatos:', error);
    }
  }

  mostrarModal = false;
  novoCandidato = { nome: '', foto: '' };

  abrirModal() {
    this.mostrarModal = true;
  }

  fecharModal() {
    this.mostrarModal = false;
    this.novoCandidato = { nome: '', foto: '' };
  }

  async adicionarCandidato(): Promise<void> {
    if (this.novoCandidato.nome && this.novoCandidato.foto) {
      try {
        const response = await api.post('/candidato', {
          nome: this.novoCandidato.nome,
          foto: this.novoCandidato.foto,
        });
        this.candidatos.push(response.data);
        this.fecharModal();
        this.novoCandidato = { nome: '', foto: '' }; // limpa o formulário
      } catch (error: any) {
        if (error.response && error.response.status === 400) {
          alert('Erro: nome já existe.');
        } else {
          console.error('Erro ao adicionar candidato:', error);
          alert('Erro interno ao adicionar candidato.');
        }
      }
    }
  }

  votar(candidato: any) {
    alert(`Você votou em ${candidato.nome}!`);
  }
}
