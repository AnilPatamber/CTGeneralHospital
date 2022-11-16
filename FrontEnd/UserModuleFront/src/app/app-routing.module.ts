import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { componentStringsDefault } from '@cds/core';
import { ManageEmployeeComponent } from './admin/manage-employee/manage-employee.component';
import { ManagePatientComponent } from './admin/manage-patient/manage-patient.component';
import { ManageUsersComponent } from './admin/manage-users/manage-users.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { PatientSuccessComponent } from './patient-success/patient-success.component';
import { RegisterComponent } from './register/register.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';

const routes: Routes = [
  
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path:'forgot', component:ForgotPasswordComponent},
  { path:'reset', component:ResetPasswordComponent},
  { path:'manage-users', component:ManageUsersComponent},
  { path:'manage-employee',component:ManageEmployeeComponent},
  { path:'manage-patient', component:ManagePatientComponent},
  { path:'patient-login-success',component:PatientSuccessComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
