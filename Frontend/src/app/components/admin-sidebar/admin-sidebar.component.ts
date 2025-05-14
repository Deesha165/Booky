import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-admin-sidebar',
    standalone: true,
    imports: [CommonModule, RouterLink, RouterLinkActive],
    template: `
        <div class="sidebar">
            <div class="sidebar-header">
                <i class="fas fa-user-shield"></i>
                <h3>Admin Dashboard</h3>
            </div>
            <nav class="sidebar-nav">
                <a routerLink="/admin-dashboard" routerLinkActive="active" class="nav-item">
                    <i class="fas fa-home"></i>
                    <span>Home</span>
                </a>
                <a routerLink="/admin-dashboard/users" routerLinkActive="active" class="nav-item">
                    <i class="fas fa-users"></i>
                    <span>Users</span>
                </a>
                <a routerLink="/admin-dashboard/transactions" routerLinkActive="active" class="nav-item">
                    <i class="fas fa-exchange-alt"></i>
                    <span>Transactions</span>
                </a>
                <a routerLink="/admin-dashboard/settings" routerLinkActive="active" class="nav-item">
                    <i class="fas fa-cog"></i>
                    <span>Settings</span>
                </a>
            </nav>
            <div class="sidebar-footer">
                <button class="logout-btn" (click)="logout()">
                    <i class="fas fa-sign-out-alt"></i>
                    <span>Logout</span>
                </button>
            </div>
        </div>
    `,
    styles: [`
        .sidebar {
            width: 250px;
            height: 100vh;
            background-color: #2c3e50;
            color: white;
            padding: 1rem;
            position: fixed;
            left: 0;
            top: 0;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            display: flex;
            flex-direction: column;
        }

        .sidebar-header {
            padding: 1rem 0;
            text-align: center;
            border-bottom: 1px solid rgba(255,255,255,0.1);
            margin-bottom: 1rem;
        }

        .sidebar-header i {
            font-size: 2.5rem;
            margin-bottom: 0.5rem;
            color: #3498db;
        }

        .sidebar-header h3 {
            margin: 0;
            font-size: 1.2rem;
            color: #ecf0f1;
        }

        .sidebar-nav {
            display: flex;
            flex-direction: column;
            gap: 0.5rem;
            flex: 1;
        }

        .nav-item {
            display: flex;
            align-items: center;
            padding: 0.8rem 1rem;
            color: #ecf0f1;
            text-decoration: none;
            border-radius: 5px;
            transition: all 0.3s ease;
        }

        .nav-item i {
            margin-right: 1rem;
            width: 20px;
            text-align: center;
        }

        .nav-item:hover {
            background-color: rgba(255,255,255,0.1);
        }

        .nav-item.active {
            background-color: #3498db;
            color: white;
        }

        .sidebar-footer {
            margin-top: auto;
            padding-top: 1rem;
            border-top: 1px solid rgba(255,255,255,0.1);
        }

        .logout-btn {
            width: 100%;
            display: flex;
            align-items: center;
            padding: 0.8rem 1rem;
            background: none;
            border: none;
            color: #e74c3c;
            cursor: pointer;
            border-radius: 5px;
            transition: all 0.3s ease;
            font-size: 1rem;
        }

        .logout-btn i {
            margin-right: 1rem;
            width: 20px;
            text-align: center;
        }

        .logout-btn:hover {
            background-color: rgba(231, 76, 60, 0.1);
        }

        @media (max-width: 768px) {
            .sidebar {
                width: 60px;
                padding: 0.5rem;
            }

            .sidebar-header h3,
            .nav-item span,
            .logout-btn span {
                display: none;
            }

            .nav-item,
            .logout-btn {
                justify-content: center;
                padding: 1rem;
            }

            .nav-item i,
            .logout-btn i {
                margin: 0;
                font-size: 1.2rem;
            }
        }
    `]
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