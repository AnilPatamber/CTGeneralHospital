import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Patient } from '../model/patient';
import { Login } from '../model/login';
import { DatePipe } from '@angular/common';
import { Pass } from '../model/password/pass';

@Injectable({
  providedIn: 'root'
})
export class PatientAuthServiceService {
  checkLogin(loginUser: Login) {
    throw new Error('Method not implemented.');
  }
  baseURL:string ="http://localhost:8084/"

  constructor(private http: HttpClient, private datePipe:DatePipe) { } 
  
  getPatientByEmail(email:string): Observable<Patient>{
    return this.http.get<Patient>(this.baseURL+email);

  }
  loginPatient(login:Login){

    const header ={'content-type':'application/json',
                   'Authorization': 'Basic ' + btoa('admin@gmail.com:Admin@123')
  };
    const body = JSON.stringify(login);
    console.log("=====body====",body);
    return this.http.post(this.baseURL+"patient/login",body,{'headers':header});

  }
  registerPatient(patient:Patient){
    patient.contactNumber="+91"+patient.contactNumber;
    patient.dateOfBirth = this.datePipe.transform(patient.dateOfBirth,'yyyy-MM-dd');
    const header ={'content-type':'application/json'};
    const body = JSON.stringify(patient);
    console.log("=====body====",body);
    return this.http.post(this.baseURL+"patient/register",body,{'headers':header});
  }
  generateDefaultPassword(email:string){
    const header ={'content-type':'application/json'};
    const body = JSON.stringify("{}");
    console.log("=====body====",body);
    return this.http.post<any>(this.baseURL+"patient/forgotpassword/"+email,body);
  }
  resetPassword(passwordInfo:Pass,email:string){
    const header ={'content-type':'application/json'};
    const body = JSON.stringify(passwordInfo);
    console.log("=====body====",body);
    return this.http.post<any>(this.baseURL+"patient/changepassword/"+email,body,{'headers':header});

  }
  getAllPatients(){    

      const header ={'content-type':'application/json',
                     'Authorization': 'Basic ' + btoa('anilpatamber@gmail.com:Admin@2050')
    };
      const body = JSON.stringify(['']);
      return this.http.get<any>(this.baseURL+"auth/patients/",{'headers':header});  
    
  }

  
}
