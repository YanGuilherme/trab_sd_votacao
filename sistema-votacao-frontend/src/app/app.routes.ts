import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { VotacaoComponent } from './pages/votacao/votacao.component';

export const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' }, // Redirecionar vazio para "inicio"

  { path: 'login', component: LoginComponent },
  { path: 'votacao/:id', component: VotacaoComponent },
];
