import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Login } from '../model/login';

@Injectable({
  providedIn: 'root'
})
export class AdminAuthService {
  baseURL:string ="http://localhost:8083/ct-pms/"

  constructor(private http: HttpClient) { }
  checkLogin(login:Login,userType:string):boolean{
    console.log(login);
    if(login.email=='admin'&&login.password=='admin'&&userType=='admin'){
      return true;
    }
    else
      return false;    
  }
  getEmployees(){
    const header ={'content-type':'application/json'};
    const body = JSON.stringify("{}");
    console.log("=====body====",body);
    return this.http.get<any>(this.baseURL+"employees");
  }
  addEmployee(){
    alert("addEmployee() called")
  }
}
