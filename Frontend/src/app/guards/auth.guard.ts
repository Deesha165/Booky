import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot } from '@angular/router';
import { TokenService } from '../services/token.service';
import { UserClaims } from '../models/user/user-claims.model';
import { UserRole } from '../enums/user-role.model';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private tokenService: TokenService) {}

  canActivate(route: ActivatedRouteSnapshot): Observable<boolean> {
  
      const expectedRoles: UserRole[] = route.data['roles'];

    return this.tokenService.getUserClaims().pipe(
      map((claims: UserClaims) => {
        if (expectedRoles.some(role=>role===claims.userRole)) {
          return true;

        } else {
          this.router.navigate(['login']);
          return false;
        }
      }),
      catchError(err => {
        this.router.navigate(['login']);
        return of(false);
      })
    );
  }
}
