import {Component} from '@angular/core';
import {ApiService} from '../../services/api.service';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import {Message} from '../../interfaces/message.interface';
import {ErrorsEnum} from '../../enums/errors.enum';

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
  isLoading = false;
  isDataLoaded = false;

  errorText: string;
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
      this.errorText = null;
      this.apiService.getMessages(this.phoneFormControl.value).subscribe(
        (res: Array<Message>) => {
          this.messages = res;
          if (this.messages && this.messages.length > 0) {
            this.errorText = ErrorsEnum.NO_MESSAGES_ERROR;
          }
          this.isDataLoaded = true;
          this.isLoading = false;
        },
        () => {
          this.errorText = ErrorsEnum.LOADING_ERROR;
          this.isLoading = false;
        });
    }
  }
}
