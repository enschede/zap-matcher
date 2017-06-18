import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TestBed, async} from '@angular/core/testing';
import {BaseRequestOptions, Http} from '@angular/http';
import {MockBackend} from '@angular/http/testing';
import {RouterTestingModule} from '@angular/router/testing';

import {AppComponent} from './app.component';
import {LoginService, RegistrationService, RememberMeService} from '../service/index';
import {LoginComponent, RegistrationComponent} from '../component/index';

export function httpFactory(backend: MockBackend, options: BaseRequestOptions) {
  return new Http(backend, options);
}

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        FormsModule,
        ReactiveFormsModule,
        RouterTestingModule
      ],
      declarations: [
        AppComponent,
        LoginComponent,
        RegistrationComponent
      ],
      providers: [
        BaseRequestOptions,
        {provide: Http, useFactory: httpFactory, deps: [MockBackend, BaseRequestOptions]},
        LoginService,
        MockBackend,
        RegistrationService,
        RememberMeService
      ]
    }).compileComponents();
  }));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

  it('should render title in a h1 tag', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('ZZP Matcher');
  }));
});
