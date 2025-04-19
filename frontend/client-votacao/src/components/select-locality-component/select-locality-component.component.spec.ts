import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectLocalityComponentComponent } from './select-locality-component.component';

describe('SelectLocalityComponentComponent', () => {
  let component: SelectLocalityComponentComponent;
  let fixture: ComponentFixture<SelectLocalityComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectLocalityComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectLocalityComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
