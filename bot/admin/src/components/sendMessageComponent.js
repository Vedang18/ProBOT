import React from 'react';
import Table, { TableBody, TableCell, TableHead, TableRow } from 'material-ui-next/Table';
import Checkbox from 'material-ui-next/Checkbox';

let id = 0;
const createData = (name, channel) => {
    id += 1;
    return {id, name, channel};
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
    render(){
        const { onSelectAllClick, numSelected, rowCount } = this.props;
        return(
            <TableHead>
                <TableRow>
                    <TableCell padding="checkbox">
                        <Checkbox 
                            indeterminate={numSelected > 0 && numSelected < rowCount}
                            checked={numSelected == rowCount}
                            onChange={onSelectAllClick}
                        />
                    </TableCell>
                    <TableCell>
                    </TableCell>
                    <TableCell>
                    </TableCell>
                </TableRow>
            </TableHead>
        );
    }
}

class SendMessageComponent extends React.Component {
    constructor(props) {
        super(props);
    }

    handleChange = (event) => {

    }

    handleSubmit = () => {

    }

    render() {
        return (
            <div>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>User</TableCell>
                            <TableCell>Channel</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {data.map(n => {
                           return (
                            <TableRow key={n.id}>
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