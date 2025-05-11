import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { VotacaoComponent } from './pages/votacao/votacao.component';
import { BemvindoComponent } from './pages/bemvindo/bemvindo.component';

export const routes: Routes = [
  { path: '', redirectTo: 'bemvindo', pathMatch: 'full' }, // Redirecionar vazio para "inicio"

  { path: 'bemvindo', component: BemvindoComponent },
  { path: 'login', component: LoginComponent },
  { path: 'votacao', component: VotacaoComponent },
];
