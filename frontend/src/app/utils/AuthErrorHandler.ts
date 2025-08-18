import { Observable, throwError } from "rxjs";
import { catchError, switchMap } from "rxjs/operators";
import { TokenService } from "../services/token.service";

export class AuthErrorHandler {
  constructor(private tokenService: TokenService) {}

  
  handleAuthError<T>(retryFn: () => Observable<T>): (source: Observable<T>) => Observable<T> {
    return catchError((error: any) => {
      if (error.status === 401) {
        return this.tokenService.refreshToken().pipe(
          switchMap(newToken => {

            localStorage.setItem('accessToken', newToken.accessToken);
            return retryFn();
          }),

          catchError(refreshError => throwError(() => refreshError))
        );
      }

      return throwError(() => error);
    });
  }
}
