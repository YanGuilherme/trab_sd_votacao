import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  email = '';
  senha = '';

  constructor(private router: Router) {}

  login(): void {
    if (this.email === 'admin@admin.com' && this.senha === 'admin') {
      // Vai para o painel de administrador
      this.router.navigate(['/administrador']);
    } else {
      // Usuário comum
      this.router.navigate(['/home']);
    }
  }
}
