import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Pass } from '../model/password/pass';
import { Patient } from '../model/patient';
import { PatientAuthServiceService } from '../service/patient-auth-service.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  passwordInfo = new Pass();
  username:string;

  constructor(private patientService:PatientAuthServiceService, private router:Router) { }

  ngOnInit(): void {
    
  }
  reset(){

    this.patientService.resetPassword(this.passwordInfo,this.username).subscribe((resData:any)=>{
      console.log('resetting password...',resData);
      alert('Reset password success, Please login with new password');
      this.router.navigate(['/login']);
    },
    (errorData: any) => {
      console.log('error',errorData);
      alert('Password reset failed');
      this.router.navigate(['/reset']);
    });
  }

}
