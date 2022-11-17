import { TestBed } from '@angular/core/testing';

import { PatientAuthServiceService } from './patient-auth-service.service';

describe('PatientAuthServiceService', () => {
  let service: PatientAuthServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PatientAuthServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
