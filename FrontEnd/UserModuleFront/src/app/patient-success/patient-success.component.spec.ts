import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientSuccessComponent } from './patient-success.component';

describe('PatientSuccessComponent', () => {
  let component: PatientSuccessComponent;
  let fixture: ComponentFixture<PatientSuccessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientSuccessComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatientSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
