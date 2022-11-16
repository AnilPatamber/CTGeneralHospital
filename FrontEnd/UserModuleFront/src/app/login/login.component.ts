import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Login } from '../model/login';
import { Patient } from '../model/patient';
import { AdminAuthService } from '../service/admin-auth.service';
import { PatientAuthServiceService } from '../service/patient-auth-service.service';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  patient :Patient;
  rememberMe:any;
  password:any;
  username:any;
  userType:any;
  showError:boolean=false;

  constructor(private patientAuthServiceService:PatientAuthServiceService, private adminAuth:AdminAuthService, private router : Router) { }

  ngOnInit(): void {
  }
  loginTest(){
      this.showError=!this.showError;
      console.log("Username and password",this.username,this.password);
      this.patientAuthServiceService.getPatientByEmail("Mustaq123@gmail.com").subscribe(
        (data)=>{ 
          console.log("------data-----",data);
          this.patient=data;
        });
        console.log(this.patient);

  }
  login(){
    // login:Login;
    const loginUser = new Login();
    loginUser.email=this.username;
    loginUser.password=this.password;
    
    console.log("=====login obj=======",loginUser)
    // try{
    //   this.patientAuthServiceService.loginPatient(login).subscribe(
    //     (data)=>{ 
    //       console.log("------login details-----",data);
    //       if(data["httpStatusCode"]==200){
    //         localStorage.setItem("email",login.email);
    //       }
    //     });
    // }catch(e){
    //   localStorage.setItem("email","abcdefgh");
    // }
    this.patientAuthServiceService.loginPatient(loginUser).subscribe((resData: any) => {
      console.log('Authentication Successfull');
      this.router.navigate(['']);
      alert('Login Successfull');
      localStorage.setItem("email",loginUser.email);
    },
    (errorData: any) => {
      console.log('error',errorData);
      alert('bad Credentials');
      this.router.navigate(['/register']);
      localStorage.setItem("email","abcdefgh");
    });
    

  }
  loginWithRole(){
    const loginUser = new Login();
    loginUser.email=this.username;
    loginUser.password=this.password;
    console.log(loginUser);
    if(this.userType=='admin'){    
      if(this.adminAuth.checkLogin(loginUser,this.userType)){
      alert('Login successfull');
      this.router.navigate(['/manage-users']);
    }
    else{
      console.error('Incorrect ID password');
      alert('Incorrect credentials');
      this.router.navigate(['/login']);    
    }
  }
  else if(this.userType=='Patient'){
    console.log("inside role patient")
    this.patientAuthServiceService.loginPatient(loginUser).subscribe((resData:any)=>{
      console.log('login Successfull',resData);
      alert('login Success');
      this.router.navigate(['/patient-login-success']);
    },
    (errorData: any) => {
      console.log('error',errorData);
      alert('Wrong credentials');
    });

  }

  }


}
