import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { apiBase } from '../../service/api';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginNick = '';
  createNick = '';

  constructor(private router: Router) {}

  async login(): Promise<void> {
    if (this.loginNick) {
      try {
        const response = await apiBase.post('/user/token', {
          nick: this.loginNick,
        });
        localStorage.setItem('token', response.data);
        this.router.navigate(['/list-candidate']);
      } catch (error: any) {
        alert(error?.response?.data || 'Erro ao fazer login.');
      }
    } else {
      alert('Por favor, insira um nick para entrar.');
    }
  }

  async createAccount(): Promise<void> {
    if (this.createNick) {
      try {
        const response = await apiBase.post('/user', { nick: this.createNick });
        localStorage.setItem('token', response.data);
        this.router.navigate(['/list-candidate']);
      } catch (error: any) {
        alert(error?.response?.data || 'Erro ao criar conta.');
      }
    } else {
      alert('Por favor, crie um nick para continuar.');
    }
  }
}
