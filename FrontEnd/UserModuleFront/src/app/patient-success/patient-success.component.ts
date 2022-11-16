import { Component, OnInit } from '@angular/core';
import { Patient } from '../model/patient';
import { PatientAuthServiceService } from '../service/patient-auth-service.service';

@Component({
  selector: 'app-patient-success',
  templateUrl: './patient-success.component.html',
  styleUrls: ['./patient-success.component.css']
})
export class PatientSuccessComponent implements OnInit {
  user:any;

  constructor(private patientService:PatientAuthServiceService) { }

  ngOnInit(): void {
    this.patientService.getPatientByEmail('ram1@gmail.com').subscribe(response => {
      console.log("====Data from server======",response);
      this.user = response;
  })

  }

}
