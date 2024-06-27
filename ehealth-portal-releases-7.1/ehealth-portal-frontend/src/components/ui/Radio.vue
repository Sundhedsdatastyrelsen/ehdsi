<!-- ---------------------------------------  HTML -->

<template>
	<div class="input-wrapper input-radio" ref="inputWrapper">
		<legend>
			<div class="label">
				{{ label }}
				<span v-if="mandatory || specialMandatory">*</span>
			</div>
			<div v-for="(e, idx1) in options" class="option">
				<label>
					<input
						:name="radio + idx1"
						type="radio"
						@keyup="validate"
						@blur="validate"
						:value="e"
						v-model="value"
						:required="mandatory"
						:animation="{ delay: idx * 0.1, x: 200 }"
					/>
					{{ e }}
				</label>
			</div>
		</legend>
		<div class="error" v-if="error">
			{{ error }}
		</div>
	</div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { onMounted, ref } from "vue";
import { store } from "../../store";
import { fly } from "../../animations";

export default {
	setup(props) {
		const input = ref(null);
		const labelTxt = ref(null);
		onMounted(() => {
			// fly(input.value, props.animation);
			// input.value.onpaste = (event) => {
			// 	var event = new Event("change");
			// 	input.value.dispatchEvent(event);
			// };
			// let animationLabel = props.animation;
			// animationLabel.delay += 0.2;
			// fly(labelTxt.value, animationLabel);
		});

		return {
			input,
			labelTxt,
		};
	},

	props: {
		animation: Object,
		label: String,
		options: Array,
		name: String,
		disabled: Boolean,
		type: String,
		modelValue: String,
		pattern: String,
		mandatory: String,
		specialMandatory: String,
	},

	data() {
		return {
			error: null,
		};
	},

	computed: {
		loading() {
			return this.$store.getters.loading;
		},
		hasError() {
			if (this.error) return this.error?.length > 1;
		},
		value: {
			get() {
				return this.modelValue;
			},
			set(value) {
				this.$emit("update:modelValue", value);
			},
		},
		regexp() {
			let regParts = this.pattern.match(/^\/(.*?)\/([gim]*)$/);
			if (regParts) {
				// the parsed pattern had delimiters and modifiers. handle them.
				return regParts[1];
			} else {
				return this.pattern;
			}
		},
	},
	methods: {
		validate(v) {
			this.valid = v.target.validity.valid;
			this.error = v.target.validationMessage;
			this.$emit("validate", this.name, v.target.validity.valid);
		},
	},
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.input-radio {
	margin-bottom: 1rem;
	label,
	legend {
		.label {
			text-transform: uppercase;
			font-weight: 500;
			color: #666;
			margin-bottom: 0.15rem;
			display: block;
			font-size: 0.9rem;
			margin-bottom: 0.25rem;
		}
	}
	input {
		padding: 0.75rem;
		border: 1px solid var(--green);
		width: 2rem !important;
		outline: none;
		border-radius: 3px;
		background-color: var(--light-green);
		transition: border-color 0.5s, background-color 0.5s, box-shadow 0.25s;
		&.error {
			border-color: var(--red);
			background-color: var(--red);
			color: #fff;
		}
		&:focus {
			box-shadow: none;
			&.error {
				background-color: var(--red);
			}
		}
	}

	.error {
		font-family: var(--font-error);
		color: var(--red);
		font-size: 0.8rem;
	}
}
</style>
