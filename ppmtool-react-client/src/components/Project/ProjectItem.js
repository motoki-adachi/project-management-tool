import React, { Component } from 'react'
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { deleteProject } from "../../actions/projectActions";

class ProjectItem extends Component {

    onDeleteClick = id => {
        this.props.deleteProject(id);
    };

    render() {
        const { project } = this.props;
        return (
            <div className="container">
                <div className="card card-body bg-light mb-3">
                    <div className="row">
                        <div className="col-2">
                            <span className="mx-auto">{project.projectIdentifer}</span>
                        </div>
                        <div className="col-lg-6 col-md-4 col-8">
                            <h3>{project.projectName}</h3>
                            <p>{project.description}</p>
                        </div>
                        <div className="col-md-4 d-none d-lg-block">
                            <ul className="list-group">
                                <Link to={`/projectBoard/${project.projectIdentifer}`}>
                                    <li className="list-group-item board">
                                        <i className="fa fa-flag-checkered"> Project Board </i>
                                    </li>
                                </Link>
                                <Link to={`/updateProject/${project.projectIdentifer}`}>
                                    <li className="list-group-item update">
                                        <i className="fa fa-edit"> プロジェクトの編集</i>
                                    </li>
                                </Link>
                                <a href="#">
                                    <li className="list-group-item delete"
                                        onClick={this.onDeleteClick.bind(this, project.projectIdentifer)}>
                                        <i className="fa fa-minus-circle"> Delete Project</i>
                                    </li>
                                </a>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

ProjectItem.propTypes = {
    deleteProject: PropTypes.func.isRequired
}

export default connect(null, { deleteProject })(ProjectItem);
