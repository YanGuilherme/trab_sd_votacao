import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './home.component.html',
})
export class HomeComponent {
  pesquisa = '';
  votacoes = [
    { id: '1', nome: 'Prefeito 2025', cidade: 'São Paulo' },
    { id: '2', nome: 'Governador 2025', cidade: 'Rio de Janeiro' },
    { id: '3', nome: 'Vereador 2025', cidade: 'Belo Horizonte' },
  ];

  constructor(private router: Router) {}

  get votacoesFiltradas() {
    return this.votacoes.filter(v =>
      v.nome.toLowerCase().includes(this.pesquisa.toLowerCase()) ||
      v.cidade.toLowerCase().includes(this.pesquisa.toLowerCase())
    );
  }

  acessarVotacao(id: string): void {
    this.router.navigate(['/votacao', id]);
  }
}
