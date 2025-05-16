import { Component, Renderer2 } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TranslateModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
 
   isDarkMode: boolean = false;

  // constructor(private renderer: Renderer2) {}

  // ngOnInit() {




  //   this.isDarkMode = localStorage.getItem('theme') === 'dark';
    
  //   if (this.isDarkMode) {
      
  //     this.renderer.addClass(document.body, 'dark-theme');
  //   }
  // }

  // toggleTheme() {
  //   this.isDarkMode = !this.isDarkMode;
  //   if (this.isDarkMode) {
  //     this.renderer.addClass(document.body, 'dark-theme');

  //     localStorage.setItem('theme', 'dark');
  //   } else {
  //     this.renderer.removeClass(document.body, 'dark-theme');
  //     localStorage.setItem('theme', 'light'); 
  //   }
  // }


  title = 'angular-i18n';
  selectedLanguage = 'en';

  constructor(private translateService: TranslateService){}

  onLanguageChange() {
    this.translateService.use(this.selectedLanguage)
  }
}
