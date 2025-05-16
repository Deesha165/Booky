import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-admin-sidebar',
    standalone: true,
    imports: [CommonModule, RouterLink, RouterLinkActive],
    template: './sidebar.component.html',
    styles: './sidebar.component.css'
})
export class AdminSidebarComponent {
    constructor(
        private authService: AuthService,
        private router: Router
    ) {}

    logout(): void {
        this.authService.logout();
        this.router.navigate(['/login']);
    }
} 