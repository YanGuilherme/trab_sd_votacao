import { Routes } from '@angular/router';
import { InicioComponent } from './pages/inicio/inicio.component';
import { LoginComponent } from './pages/login/login.component';
import { RegistroComponent } from './pages/registro/registro.component';
import { HomeComponent } from './pages/home/home.component';
import { VotacaoComponent } from './pages/votacao/votacao.component';
import { AdministradorComponent } from './pages/administrador/administrador.component';
import { BemvindoComponent } from './pages/Bem Vindo/bemvindo.component';

export const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' }, // Redirecionar vazio para "inicio"

  { path: 'inicio', component: InicioComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'home', component: HomeComponent },
  { path: 'votacao/:id', component: VotacaoComponent },
  { path: 'administrador', component: AdministradorComponent },
  { path: 'bemvindo', component: BemvindoComponent },
];
