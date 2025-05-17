import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-settings',
  imports: [CommonModule,FormsModule,TranslateModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent {

    title = 'angular-i18n';
  selectedLanguage = 'en';

  constructor(private translateService:TranslateService){

  }

  onLanguageChange() {
    this.translateService.use(this.selectedLanguage);
      localStorage.setItem('appLanguage', this.selectedLanguage);
  }

}
