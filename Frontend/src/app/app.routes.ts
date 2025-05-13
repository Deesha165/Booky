import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';

export const routes: Routes = [
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
