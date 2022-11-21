import React, {useState} from 'react';
import {Button, Form, Input, InputNumber, Popconfirm, Table, Typography} from 'antd';
import axios from "axios";

const client = axios.create({
    baseURL: ""
});

// const originData = [];
// for (let i = 0; i < 100; i++) {
//     originData.push({
//         key: i.toString(),
//         name: `Edrward ${i}`,
//         age: 32,
//         address: `London Park no. ${i}`,
//     });
// }
const EditableCell = ({
                          editing,
                          dataIndex,
                          title,
                          inputType,
                          record,
                          index,
                          children,
                          ...restProps
                      }) => {
    const inputNode = inputType === 'number' ? <InputNumber/> : <Input/>;
    return (
        <td {...restProps}>
            {editing ? (
                <Form.Item
                    name={dataIndex}
                    style={{
                        margin: 0,
                    }}
                    rules={[
                        {
                            required: true,
                            message: `Please Input ${title}!`,
                        },
                    ]}
                >
                    {inputNode}
                </Form.Item>
            ) : (
                children
            )}
        </td>
    );
};
const App = () => {
    const [form] = Form.useForm();
    // const [data, setData] = useState(originData);
    const [editingKey, setEditingKey] = useState('');
    const [items, setItems] = React.useState([]);
    const isEditing = (record) => record.id === editingKey;
    const edit = (record) => {
        console.log(record);
        form.setFieldsValue({
            name: '1222',
            ...record,
        });
        setEditingKey(record.id);
    };


    function loadItems() {
        client.get("/items").then((response) => {
            setItems(response.data);
        });
    }

    React.useEffect(() => {
        loadItems();
    }, []);

    const cancel = () => {
        setEditingKey('');
    };
    const del = (record) => {
        client.delete(`/items/${record.id}`).then((response) => {
            loadItems();
        });
    };
    const test = (record) => {
        client.get(`/items/${record.id}/test`).then((response) => {
        });
    };

    const save = async (id) => {
        try {
            console.log(id)
            const row = await form.validateFields();
            const newData = [...items];
            const index = newData.findIndex((item) => id === item.id);
            if (index > -1) {
                const item = newData[index];
                newData.splice(index, 1, {
                    ...item,
                    ...row,
                });
                setItems(newData);
                setEditingKey('');
                // console.log("----------")
                // console.log(item)
            } else {
                newData.push(row);
                setItems(newData);
                setEditingKey('');
            }
            // console.log("================")
            // console.log(row)
            if (id == "NEW") {
                client.post("/items", row)
                    .then((response) => {
                        loadItems()
                    });
            } else {
                client.put(`/items/${id}`, row)
                    .then((response) => {
                        loadItems()
                    });
            }

        } catch (errInfo) {
            console.log('Validate Failed:', errInfo);
        }
    };
    const handleAdd = () => {
        const newData = {
            id: 'NEW',
            name: '',
            type: 'single',
            notify: true,
        };
        setItems([newData, ...items]);
    };
    const view = () => {
        window.open("http://172.16.205.243:5601/app/discover#/view/48850470-542a-11ed-bb22-675f5012cdd2?_g=(filters:!(),refreshInterval:(pause:!t,value:0),time:(from:now%2Fd,to:now%2Fd))&_a=(columns:!(name,price,catchTime),filters:!(),grid:(),hideChart:!f,index:'43ba74f0-2a65-11ed-bb22-675f5012cdd2',interval:auto,query:(language:kuery,query:''),sort:!(!(catchTime,desc)),viewMode:documents)")
    }
    const viewDB = () => {
        window.open("/h2")
    }
    const columns = [
        {
            title: 'id',
            dataIndex: 'id',
            width: '40%',
            editable: false,
            hidden: true,
        },
        {
            title: 'name',
            dataIndex: 'name',
            width: '40%',
            editable: true,
        },
        {
            title: 'type',
            dataIndex: 'type',
            width: '5%',
            editable: true,
        },
        {
            title: 'expect',
            dataIndex: 'expect',
            width: '5%',
            editable: true,
        },
        {
            title: 'notify',
            dataIndex: 'notify',
            width: '5%',
            editable: true,
            render: (_, record) => {
                return record.notify ? "true" : "false";
            }
        },
        {
            title: 'url',
            dataIndex: 'url',
            width: '30%',
            editable: true,
        },
        {
            title: 'operation',
            dataIndex: 'operation',
            render: (_, record) => {
                const editable = isEditing(record);
                return editable ? (
                    <span>
                        <Typography.Link
                            onClick={() => save(record.id)}
                            style={{
                                marginRight: 8,
                            }}
                        >
                          Save
                        </Typography.Link>

                        <Popconfirm title="Sure to cancel?" onConfirm={cancel}>
                          <a>Cancel</a>
                        </Popconfirm>
                      </span>
                ) : (
                    <div>
                        <Typography.Link disabled={editingKey !== ''} onClick={() => edit(record)} style={{
                            marginRight: 8,
                        }}>
                            Edit
                        </Typography.Link>
                        <Typography.Link disabled={editingKey !== ''} onClick={() => del(record)} style={{
                            marginRight: 8,
                        }}>
                            Del
                        </Typography.Link>
                        <Typography.Link disabled={editingKey !== ''} onClick={() => test(record)}>
                            Test
                        </Typography.Link>
                    </div>
                );
            },
        },
    ];
    const mergedColumns = columns.map((col) => {
        if (!col.editable) {
            return col;
        }
        return {
            ...col,
            onCell: (record) => ({
                record,
                inputType: col.dataIndex === 'expect' ? 'number' : 'text',
                dataIndex: col.dataIndex,
                title: col.title,
                editing: isEditing(record),
            }),
        };
    });
    return (

        <Form form={form} component={false}  >
            <Button onClick={handleAdd} type="primary" style={{marginBottom: 16}}>
                Add
            </Button>
            <Button onClick={view}  style={{marginBottom: 16,marginLeft: 16}}>
                ES
            </Button>
            <Button onClick={viewDB}  style={{marginBottom: 16,marginLeft: 16}}>
                DB
            </Button>
            <Table
                rowKey={(record) => record.id}
                components={{
                    body: {
                        cell: EditableCell,
                    },
                }}
                bordered
                dataSource={items}
                columns={mergedColumns}
                rowClassName="editable-row"
                pagination={false}
                style={{width: "100%"}}
            />
        </Form>
    );
};
export default App;