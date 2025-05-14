import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { EventComponent } from './pages/event/event.component';
import { CustomereventComponent } from './pages/home/customer-store.component';
import { BookingStatusScreenComponent } from './pages/booking-status-screen/booking-status-screen.component';

export const routes: Routes = [
    {
        path:'event/:id'
    ,
    component:EventComponent
    },
    {
path:'home',
component:CustomereventComponent
    },
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
