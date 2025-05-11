import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-votacao',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './votacao.component.html',
})
export class VotacaoComponent {
  id: string | null = null;
  jaVotou = false;
  candidatos = [
    { numero: 10, nome: 'Candidato 1', slogan: 'Avançar Juntos', votos: 0 },
    { numero: 20, nome: 'Candidato 2', slogan: 'Renovar é Preciso', votos: 0 },
  ];

  constructor(private route: ActivatedRoute) {
    this.id = this.route.snapshot.paramMap.get('id');
  }

  votar(numero: number): void {
    if (this.jaVotou) return;
    const c = this.candidatos.find(x => x.numero === numero);
    if (c) {
      c.votos++;
      this.jaVotou = true;
    }
  }
}
