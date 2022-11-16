import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Login } from '../model/login';
import { Patient } from '../model/patient';
import { PatientAuthServiceService } from '../service/patient-auth-service.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  patient :Patient;
  rememberMe:any;
  password:any;
  username:any;
  userType:any;
  showError:boolean=false;

  constructor(private patientAuthServiceService:PatientAuthServiceService,private router : Router) { }

  ngOnInit(): void {
  }
  forgotPassword(){
    this.patientAuthServiceService.generateDefaultPassword(this.username).subscribe((resData:any)=>{
      console.log('mail with default password sent',resData);
      alert('Mail with default password sent');
      this.router.navigate(['/reset']);
    },
    (errorData: any) => {
      console.log('error',errorData);
      alert('Error encounterred while sending default password ');
    });

    alert("Default password email send");
  }

}
