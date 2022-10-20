import React from 'react';
import ReactDOM from 'react-dom/client';
import styles from './index.css';
import App from './App';

class EntandoUpgradeMfe extends HTMLElement {
  #rootID = 'app-element-id'
  #styleID = 'app-styles-element-id'
  #appInstance = null

  constructor() {
    super();

    this.attachShadow({ mode: 'open' });
  }

  connectedCallback() {
    this.render()
  }

  cleanTree() {
    const currentElement = this.shadowRoot.getElementById(this.#rootID);

    if (currentElement) {
      this.shadowRoot.removeChild(currentElement);
    }

    const currentStyleElement = this.shadowRoot.getElementById(this.#styleID);

    if (currentStyleElement) {
      this.shadowRoot.removeChild(currentStyleElement);
    }

    this.#appInstance?.unmount();
  }

  render() {
    const element = document.createElement('div');
    const styleElement = document.createElement('style');

    styleElement.innerHTML = styles.toString();

    styleElement.id = this.#styleID;
    element.id = this.#rootID;

    this.cleanTree();

    this.#appInstance = ReactDOM.createRoot(element);

    this.#appInstance.render(
      <React.StrictMode>
        <App />
      </React.StrictMode>
    );

    this.shadowRoot.appendChild(styleElement);
    this.shadowRoot.appendChild(element);
  }
}

customElements.define('entando-upgrade-mfe', EntandoUpgradeMfe);