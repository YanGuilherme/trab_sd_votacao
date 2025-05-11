import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-bemvindo',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './bemvindo.component.html',
  styleUrls: ['./bemvindo.component.css'], // Você pode adicionar múltiplos arquivos
})
export class BemvindoComponent {}
