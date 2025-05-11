import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './registro.component.html',
})
export class RegistroComponent {
  nome = '';
  email = '';
  idade: number | null = null;
  cidade = '';

  constructor(private router: Router) {}

  registrar(): void {
    console.log('Usuário registrado:', { nome: this.nome, email: this.email, idade: this.idade, cidade: this.cidade });
    this.router.navigate(['/home']);
  }
}
