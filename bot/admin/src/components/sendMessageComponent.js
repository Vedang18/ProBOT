import React from 'react';
import Table, { TableBody, TableCell, TableHead, TableRow } from 'material-ui-next/Table';
import Checkbox from 'material-ui-next/Checkbox';

let id = 0;
const createData = (name, channel) => {
  id += 1;
  return { id, name, channel };
}
const data = [
  createData("MasterChief", "Skype"),
  createData("Abhishek", "Slack"),
  createData("Aditya", "Skype"),
  createData("Suzanne", "Skype"),
  createData("Sujata", "Slack"),
  createData("Komal", "slack")
]

class EnhancedTableHead extends React.Component {
  render() {
    const { onSelectAllClick, numSelected, rowCount } = this.props;
    return (
      <TableHead>
        <TableRow>
          <TableCell padding="checkbox">
            <Checkbox
              indeterminate={numSelected > 0 && numSelected < rowCount}
              checked={numSelected == rowCount}
              onChange={onSelectAllClick}
            />
          </TableCell>
          <TableCell>User</TableCell>
          <TableCell>Channel</TableCell>
        </TableRow>
      </TableHead>
    );
  }
}

class SendMessageComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      selected: []
    }
  }

  handleRowClick = (event, id) => {
    const { selected } = this.state;
    const selectedIndex = selected.indexOf(id);
    let newSelected = [];

    if (selectedIndex === -1) { // item was not selected earlier; now is selected
      newSelected = newSelected.concat(selected, id);
    } else if (selectedIndex === 0){ // 1st item was selected, remove it
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) { // last item was selected, remove it
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) { // some item in between was selected, remove it
      newSelected = newSelected.concat(
        selected.slice(0, selectedIndex),
        selected.slice(selectedIndex+1)
      );
    }
    // this.setState({selected: newSelected}, function(){ console.log(data.filter(d => this.state.selected.indexOf(d.id) !== -1))});
    this.setState({selected: newSelected});
    
  }

  handleChange = (event) => {

  }

  handleSubmit = () => {

  }

  isSelected = (id) => this.state.selected.indexOf(id) !== -1;

  render() {
    return (
      <div>
        <Table>
          <EnhancedTableHead>
          </EnhancedTableHead>
          <TableBody>
            {data.map(n => {
              const isSelected = this.isSelected(n.id);
              return (
                <TableRow
                  hover
                  key={n.id}
                  onClick={event => this.handleRowClick(event, n.id)}
                >
                  <TableCell><Checkbox checked={isSelected} /></TableCell>
                  <TableCell>{n.name}</TableCell>
                  <TableCell>{n.channel}</TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </div>
    );
  }
}

export default SendMessageComponent;