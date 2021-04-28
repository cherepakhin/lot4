import { Component } from '@angular/core';
import { ApiService } from '../../services/api.service';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import {Message} from '../../interfaces/message.interface';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-messages-page',
  templateUrl: './messages-page.component.html',
  styleUrls: ['./messages-page.component.scss']
})
export class MessagesPageComponent{
  isLoadingError = false;
  isLoading = false;
  isDataLoaded = false;

  messages: Array<Message> = [];

  phoneFormControl = new FormControl('', [
    Validators.required,
    Validators.pattern('^((\\+7|7|8|)+([0-9]){10})$'),
  ]);

  matcher = new MyErrorStateMatcher();

  constructor(public apiService: ApiService) {}

  handleEnterPress(evt): void {
    if (evt.keyCode === 13) {
      this.getMessages();
    }
  }

  getMessages(): void {
    if (this.phoneFormControl.status === 'VALID') {
      this.isLoading = true;
      this.apiService.getMessages(this.phoneFormControl.value).subscribe(
        (res: Array<Message>) => {
          this.messages = res;

          this.isDataLoaded = true;
          this.isLoading = false;
        },
        () => {
          this.isLoadingError = true;
          this.isLoading = false;
        });
    }
  }
}
