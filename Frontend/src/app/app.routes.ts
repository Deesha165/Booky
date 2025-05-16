import { Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { SignupComponent } from './pages/auth/signup/signup.component';
import { EventComponent } from './pages/event/event.component';
import { CustomereventComponent } from './pages/user/home/customer-store.component';
import { BookingStatusScreenComponent } from './pages/booking-status-screen/booking-status-screen.component';
import { AdminEventsComponent } from './pages/admin/admin-events/admin-events.component';


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
    path:'admin/event',
    component:AdminEventsComponent
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
