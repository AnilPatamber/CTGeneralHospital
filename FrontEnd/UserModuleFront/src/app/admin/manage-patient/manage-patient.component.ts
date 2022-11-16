import { Component, OnInit } from '@angular/core';
import { Patient } from 'src/app/model/patient';
import { PatientAuthServiceService } from 'src/app/service/patient-auth-service.service';

@Component({
  selector: 'app-manage-patient',
  templateUrl: './manage-patient.component.html',
  styleUrls: ['./manage-patient.component.css']
})
export class ManagePatientComponent implements OnInit {
  
  patientsList = new Array<any[]>();

  constructor(private patientService:PatientAuthServiceService) { }

  ngOnInit(): void {
    console.log("inside onit of patient manage component");
    
    this.patientService.getAllPatients().subscribe(response => {
      console.log("====Data from server======",response);
      this.patientsList=response;
      for(let patient of this.patientsList ){
        console.log(patient['email']);
      }
  });


  }
  delete(email:string){
    this.patientsList.pop();
    console.log('user removed successfully',email);

  }

}
