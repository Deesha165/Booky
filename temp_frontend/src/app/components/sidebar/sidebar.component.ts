import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-sidebar',
  imports: [TranslateModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {

       constructor(
        private authService: AuthService,
        private router: Router
    ) {}

    goToSeetings(){
      this.router.navigate(['settings'])
    }
    goToEvents(){
      this.router.navigate(['events']);
    }

    logout(): void {
        this.authService.logout();
        this.router.navigate(['/login']);
    }
}
