import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Patient } from '../model/patient';
import { PatientAuthServiceService } from '../service/patient-auth-service.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  patient = new Patient();
  todayDate=new Date();
  confirmPass:string=undefined;
  passEquals:Boolean;

  constructor(private patientService:PatientAuthServiceService,private router : Router) { }

  ngOnInit(): void {
    console.log("first pass",this.patient.password, "second pass",this.confirmPass);
    console.log("passEquals", this.passEquals);
  }
  registor(){
    // this.passEquals=this.patient.password!=this.confirmPass;
    console.log("first pass",this.patient.password, "second pass",this.confirmPass);
    console.log("passEquals", this.passEquals);
    console.log("Register called");
    console.log("Today date",this.todayDate);
    console.log("patient details", this.patient);

    this.patientService.registerPatient(this.patient).subscribe((resData:any)=>{
      console.log('registration Successfull',resData);
      alert('Registration Successfull');
      this.router.navigate(['/login']);
    },
    (errorData: any) => {
      console.log('error',errorData);
      alert('Cannot register, please enter all the required fields');
    });
  }

}
