import { Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { SignupComponent } from './pages/auth/signup/signup.component';
import { CustomereventComponent } from './pages/user/home/customer-store.component';
import { BookingStatusScreenComponent } from './pages/general/booking-status-screen/booking-status-screen.component';

import { AuthGuard } from './guards/auth.guard';
import { UserRole } from './enums/user-role.model';
import { EventsComponent } from './pages/events/admin-events.component';


export const routes: Routes = [
  
    {
path:'home',
component:CustomereventComponent,
canActivate:[AuthGuard],
data:{roles:[UserRole.USER]}
    },
{
    path:'events',
    component:EventsComponent,
    canActivate:[AuthGuard],
      data:{roles:[UserRole.ADMIN,UserRole.VERIFIER]}
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
