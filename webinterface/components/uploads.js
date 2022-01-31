import React from "react";
import { api_request, get_api_path } from "../js/api";
import Button from "./button";
import Card from "./card";

export default class Uploads extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			uploads: [],
			rendered_uploads: [],
		};

		this.request_uploads();
	}

	request_uploads() {
		api_request("/api/v2/uploads" + (this.props.all_uploads ? "?all_files" : "")).then(data => {
			data = JSON.parse(data);
			this.state.uploads = data;
			this.render_uploads();
		});
	}

	render_uploads() {
		this.state.rendered_uploads = [];
		for (let upload of this.state.uploads) {
			this.state.rendered_uploads.push(
				<Card>
					<h2><a href={get_api_path() + "/files/" + upload.file_id}>{upload.original_name}</a></h2>
					<p>Uploaded at: {new Date(upload.upload_time).toLocaleString()}</p>
					<Button onClick={
						event => {
							api_request("/api/v2/uploads/delete?file_id=" + upload.file_id).then(data => {
								alert(data);
								this.request_uploads();
							});
						}
					}>Delete Upload</Button>

					<Card>
						<h2>Uploader</h2>
						<p>Id: {upload.uploader.id} ({upload.uploader.system ? "System" : "User"})</p>
					</Card>
				</Card>
			);
		}

		this.setState(this.state);
	}

	render() {
		return (
			<div>
				{this.state.rendered_uploads}
			</div>
		);
	}
}