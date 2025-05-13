import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-candidate',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './candidate.component.html',
  styleUrl: './candidate.component.css',
})
export class CandidateComponent {
  @Input() nome!: string;
  @Input() foto!: string;
  @Input() quantidadeVotos!: number;
}
