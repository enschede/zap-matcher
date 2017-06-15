import {TestBed, async} from '@angular/core/testing';
import {BaseRequestOptions, Http} from '@angular/http';
import {MockBackend} from '@angular/http/testing';

import {AppComponent} from './app.component';
import {RegistrationService} from '../service/index';
import {RegistrationComponent} from '../component/index';

export function httpFactory(backend: MockBackend, options: BaseRequestOptions) {
  return new Http(backend, options);
}

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        RegistrationComponent
      ],
      providers: [
        BaseRequestOptions,
        {provide: Http, useFactory:httpFactory, deps: [MockBackend, BaseRequestOptions]},
        MockBackend,
        RegistrationService
      ]
    }).compileComponents();
  }));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

  it(`should have as title 'app works!'`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('app works!');
  }));

  it('should render title in a h1 tag', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('app works!');
  }));
});
