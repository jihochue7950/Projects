package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatGPT {

	private String id;
	private String object;
	private Integer created;
	private String model;
	private List<Choice> choices;
	private Usage usage;

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Choice {

		private String text;
		private Integer index;
		private Object logprobs;
		private String finish_reason;

		public Choice() {
		}

		@Override
		public String toString() {
			return "Choices [text=" + text + ", index=" + index + ", logprobs=" + logprobs + ", finish_reason="
					+ finish_reason + "]";
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public Integer getIndex() {
			return index;
		}

		public void setIndex(Integer index) {
			this.index = index;
		}

		public Object getLogprobs() {
			return logprobs;
		}

		public void setLogprobs(Object logprobs) {
			this.logprobs = logprobs;
		}

		public String getFinish_reason() {
			return finish_reason;
		}

		public void setFinish_reason(String finish_reason) {
			this.finish_reason = finish_reason;
		};

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Usage {

		private Integer prompt_tokens;
		private Integer completion_tokens;
		private Integer total_tokens;

		public Usage() {
		}

		@Override
		public String toString() {
			return "Usage [prompt_tokens=" + prompt_tokens + ", completion_tokens=" + completion_tokens
					+ ", total_tokens=" + total_tokens + "]";
		}

		public Integer getPrompt_tokens() {
			return prompt_tokens;
		}

		public void setPrompt_tokens(Integer prompt_tokens) {
			this.prompt_tokens = prompt_tokens;
		}

		public Integer getCompletion_tokens() {
			return completion_tokens;
		}

		public void setCompletion_tokens(Integer completion_tokens) {
			this.completion_tokens = completion_tokens;
		}

		public Integer getTotal_tokens() {
			return total_tokens;
		}

		public void setTotal_tokens(Integer total_tokens) {
			this.total_tokens = total_tokens;
		};

	}

	public ChatGPT() {
	}

	@Override
	public String toString() {
		return "ChatGPT [id=" + id + ", object=" + object + ", created=" + created + ", model=" + model + ", choices="
				+ choices + ", usage=" + usage + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public Integer getCreated() {
		return created;
	}

	public void setCreated(Integer created) {
		this.created = created;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public Usage getUsage() {
		return usage;
	}

	public void setUsage(Usage usage) {
		this.usage = usage;
	}

}
