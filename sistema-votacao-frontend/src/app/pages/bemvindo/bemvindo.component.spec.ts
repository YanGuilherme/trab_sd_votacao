import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BemvindoComponent } from './bemvindo.component';

describe('InicioComponent', () => {
  let component: BemvindoComponent;
  let fixture: ComponentFixture<BemvindoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BemvindoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BemvindoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
