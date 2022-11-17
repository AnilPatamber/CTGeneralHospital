import { Component, OnInit } from '@angular/core';
import { Employee } from 'src/app/model/employee';
import { Patient } from 'src/app/model/patient';
import { Person } from 'src/app/model/person';
import { AdminAuthService } from 'src/app/service/admin-auth.service';

@Component({
  selector: 'app-manage-employee',
  templateUrl: './manage-employee.component.html',
  styleUrls: ['./manage-employee.component.css']
})
export class ManageEmployeeComponent implements OnInit {

    person = new Person();
    employee= new Employee();

    constructor(private adminService:AdminAuthService) {
      this.showAll=true;
     }
    allEmployee:any;
    showAll:boolean=false;
    users = new Array<any>();
    heroes = new Array<any>();
    

    ngOnInit(): void {
      console.log(this.adminService.getEmployees());
      this.adminService.getEmployees().subscribe(response => {
      console.log("====Data from server======",response);
      this.users = response;
  });
      
    }
  getAllEmployees(){
    
  //   console.log(this.adminService.getEmployees());
  //   this.adminService.getEmployees().subscribe(response => {
  //     console.log("====Data from server======",response);
  //     this.users = response;
  // });
  this.showAll=true;

  }
  delete(name:string){
    this.users.pop();
    this.heroes.push(name); 

  }
  printUsers(){
    this.showAll=false;
    console.log("====data in users====",this.users);  
    console.log(this.heroes);
  }
  addNewEmployee(){
    this.employee.person=this.person;
    console.log(this.employee);
    this.adminService.addEmployee();

  }
}

