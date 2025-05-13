import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { BemvindoComponent } from './pages/bemvindo/bemvindo.component';
import { ListCandidateComponent } from './pages/list-candidate/list-candidate.component';

export const routes: Routes = [
  { path: '', redirectTo: 'bemvindo', pathMatch: 'full' }, // Redirecionar vazio para "inicio"

  { path: 'bemvindo', component: BemvindoComponent },
  { path: 'list-candidate', component: ListCandidateComponent },
  { path: 'login', component: LoginComponent },
];
