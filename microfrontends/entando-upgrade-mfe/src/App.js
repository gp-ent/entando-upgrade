import React from 'react';
import get from 'lodash/get';
import forEach from 'lodash/forEach';
import Table from 'react-bootstrap/Table'

class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      release_list: []
    }
  }

  goBackHome() {
    return window.entando.router.push('/dashboard')
  }

  checkVersion(data) {
    const regex = /v[0-9].[0-9].[0-9]$/gm;
    return regex.test(get(data, 'name'));
  }

  componentDidMount() {
    document.title = "Entando Releases";
    fetch('https://api.github.com/repos/entando/entando-releases/releases')
      .then(results => {
        return results.json();
      }).then(data => {
        var releases = [];

        data.filter(this.checkVersion).forEach(function(rel) {
          console.log(rel.name, rel.html_url);
          var release = {
            release_id: get(rel, 'id'),
            release_name: get(rel, 'name'),
            release_url: get(rel, 'html_url'),
            release_date: get(rel, 'published_at')
          };
          releases.push(release);
        })
        console.log(releases)
        this.setState({
          release_list: releases.sort((a, b) => (b.release_id - a.release_id))
        })
      });
  }

  getTableBody() {
    let tableData = [];
    forEach(this.state.release_list, (data) => {
      tableData.push(
        <tr key={data.release_id}>
          <td>
            {data.release_name}
          </td>
          <td>
            {data.release_date}
          </td>
          <td>
            <a href={data.release_url} target="_blank" rel="noreferrer">{data.release_url}</a>
          </td>
        </tr>
      );
    });
    return tableData;
  }

  getTable() {
    return (
      <Table>
        <thead>
          <tr>
            <th>Release</th>
            <th>Date</th>
            <th>Link</th>
          </tr>
        </thead>
        <tbody>
          {this.getTableBody()}
        </tbody>
      </Table>
    );
  }

  render() {
    return (
      <div className="App">
      <header className="App-header">
        <h1>Entando Releases</h1>
        <div className="entando_releases">
          {this.getTable()}
        </div>
        <button onClick={this.goBackHome} className="Button">
          Go back to the dashboard
        </button>
      </header>
    </div>
    );
  }
}

export default App;