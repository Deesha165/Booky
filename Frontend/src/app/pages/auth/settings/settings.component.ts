import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { UserService } from '../../../services/user.service';
import { PasswordChangeRequest } from '../../../dtos/user/password-change-request.dto';

@Component({
  selector: 'app-settings',
  imports: [CommonModule,FormsModule,TranslateModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent {

  
  selectedLanguage = 'en';

  passwordChangeRequest: PasswordChangeRequest = { oldPassword: '', newPassword: '' };
   
  constructor(private translateService:TranslateService,private userService :UserService){

  }

  onLanguageChange() {
    this.translateService.use(this.selectedLanguage);
      localStorage.setItem('appLanguage', this.selectedLanguage);
  }


  onSaveChanges(){


    this.userService.changePassword(this.passwordChangeRequest).subscribe((data)=>{
      console.log(data);
    })
    this.passwordChangeRequest={
      oldPassword:'',
      newPassword:''
    }
  }
}
