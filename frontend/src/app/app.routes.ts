import { Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { SignupComponent } from './pages/auth/signup/signup.component';
import { HomeComponent } from './pages/user/home/home.component';
import { BookingStatusScreenComponent } from './pages/general/booking-status-screen/booking-status-screen.component';

import { AuthGuard } from './guards/auth.guard';
import { UserRole } from './enums/user-role.model';
import { EventsComponent } from './pages/events/events.component';
import { VerificationComponent } from './pages/verification/verification.component';
import { SettingsComponent } from './pages/auth/settings/settings.component';


export const routes: Routes = [
  
    {
path:'home',
component:HomeComponent,
canActivate:[AuthGuard],
data:{roles:[UserRole.USER]}
    },
{
    path:'events',
    component:EventsComponent,
    canActivate:[AuthGuard],
      data:{roles:[UserRole.ADMIN]}
},{
    path:'verification',
    component:VerificationComponent,
    canActivate:[AuthGuard],
    data:{roles:[UserRole.VERIFIER]}
},
{
    path:'settings',
    component:SettingsComponent,
    canActivate:[AuthGuard]
}
    ,
    {
path:'booking-screen/:status',
component:BookingStatusScreenComponent
    },
    {
        path:'login',
        component:LoginComponent
    },
    {
path:'sign-up',
component:SignupComponent
    },
    {
          path: '**', redirectTo: 'login' 
    }
];
