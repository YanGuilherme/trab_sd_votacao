import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

interface Regiao {
  id: number;
  nome: string;
  sigla: string;
}

interface UF {
  id: number;
  nome: string;
  sigla: string;
  regiao: Regiao;
}

interface Municipio {
  id: number;
  nome: string;
}

@Component({
  selector: 'app-select-locality-component',
  imports: [FormsModule],
  templateUrl: './select-locality-component.component.html',
  styleUrl: './select-locality-component.component.css',
})
export class SelectLocalityComponentComponent implements OnInit {
  ufs: UF[] = [];
  municipios: Municipio[] = [];
  ufSelecionada: string = '';

  async ngOnInit() {
    this.ufs = await this.buscarLocalidades<UF[]>('estados');
  }

  async buscarLocalidades<T>(tipo: string, parametro?: string): Promise<T> {
    try {
      let url = `https://servicodados.ibge.gov.br/api/v1/localidades/${tipo}`;

      if (parametro) {
        url += `/${parametro}`;
      }

      const response = await fetch(url);
      const data = await response.json();

      if (tipo === 'estados' || tipo.includes('municipios')) {
        return data.sort((a: any, b: any) => a.nome.localeCompare(b.nome)) as T;
      }

      return data as T;
    } catch (err) {
      console.error(`Erro ao buscar ${tipo}:`, err);
      return [] as unknown as T;
    }
  }

  async onUfChange(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    this.ufSelecionada = selectElement.value;

    if (this.ufSelecionada) {
      this.municipios = await this.buscarLocalidades<Municipio[]>(
        `estados/${this.ufSelecionada}/municipios`
      );
    } else {
      this.municipios = [];
    }
  }
}
